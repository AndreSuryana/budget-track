package com.andresuryana.budgettrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.andresuryana.budgettrack.R
import com.andresuryana.budgettrack.core.fragment.BaseFragment
import com.andresuryana.budgettrack.databinding.FragmentHomeBinding
import com.andresuryana.budgettrack.domain.model.account.Account
import com.andresuryana.budgettrack.domain.model.expense.Expense
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.ui.home.adapter.account.AccountGridAdapter
import com.andresuryana.budgettrack.ui.home.adapter.expense.ExpenseAdapter
import com.andresuryana.budgettrack.ui.home.state.HomeUiState
import com.andresuryana.budgettrack.util.ext.CurrencyFormatter.formatCurrency
import com.andresuryana.budgettrack.util.ext.CurrencyFormatter.formatCurrencyHidden
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<HomeViewModel>()

    private val accountAdapter = AccountGridAdapter()
    private val expenseAdapter = ExpenseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefreshView()

        setupAccountList()
        setupRecentExpenseList()

        setupButtonListener()

        observeUiStates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSwipeRefreshView() {
        binding?.root?.setOnRefreshListener {
            viewModel.loadUiData()
            if (binding?.root?.isRefreshing == true) {
                binding?.root?.isRefreshing = false
            }
        }
    }

    private fun setupAccountList() {
        // Adapter item click listener
        accountAdapter.setOnItemClickListener(this::onAccountClicked)

        // Configure recycler view
        binding?.accounts?.apply {
            adapter = accountAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                RecyclerView.HORIZONTAL,
                false
            )
        }
    }

    private fun setupRecentExpenseList() {
        // Adapter item click listener
        expenseAdapter.setOnItemClickListener(this::onExpenseClicked)

        // Configure recent expense recycler view with custom date separator
        binding?.recentExpenses?.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }
    }

    private fun setupButtonListener() {
        binding?.expenseTotal?.toggleVisibility?.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleExpenseAmountVisibility(isChecked)
        }
    }

    private fun onAccountClicked(account: Account) {
        // TODO: Change the implementation later!
        showToast(
            requireContext(),
            "${account.title} - ${account.amount} - ${account.type}",
        )
    }

    private fun onExpenseClicked(expense: Expense) {
        // TODO: Change the implementation later!
        showToast(
            requireContext(),
            "${expense.title} - ${expense.amount} - ${expense.category.title}",
        )
    }

    private fun observeUiStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Greeting & Expense Total Section
                    setGreetings(it.greetingText, it.greetingDetail)
                    setExpenseCardTotal(it.expenseTotal, it.expenseTotalVisibility)

                    // Account & Recent Expenses
                    accountAdapter.submitList(it.accounts)
                    expenseAdapter.submitList(it.recentExpenses)

                    // Loading
                    showLoadingOverlay(it.isLoading)
                }
            }
        }

        viewModel.toastEvent.observe(viewLifecycleOwner) { event ->
            event.getIfNotHandled()?.let { showToast(requireContext(), it) }
        }
    }

    private fun setGreetings(text: HomeUiState.GreetingText, detail: HomeUiState.GreetingDetail) {
        binding?.greeting?.apply {
            greetingText.text = getGreetingTextFromState(text)
            when (detail) {
                is HomeUiState.GreetingDetail.Authenticated -> {
                    val user = detail.user
                    userName.text = user.name
                    userImage.load(user.picture) {
                        crossfade(true)
                        placeholder(R.drawable.placeholder_profile_image)
                        error(R.drawable.placeholder_profile_image)
                        scale(Scale.FIT)
                        transformations(CircleCropTransformation())
                    }
                }

                HomeUiState.GreetingDetail.Guest -> {
                    userName.text = getString(R.string.default_guest_user)
                }
            }
        }
    }

    private fun getGreetingTextFromState(greetingText: HomeUiState.GreetingText): String {
        return when (greetingText) {
            HomeUiState.GreetingText.MORNING -> getString(R.string.greeting_morning)
            HomeUiState.GreetingText.AFTERNOON -> getString(R.string.greeting_afternoon)
            HomeUiState.GreetingText.EVENING -> getString(R.string.greeting_evening)
            HomeUiState.GreetingText.NIGHT -> getString(R.string.greeting_night)
            HomeUiState.GreetingText.DEFAULT -> getString(R.string.greeting_default)
        }
    }

    private fun setExpenseCardTotal(expenseTotal: ExpenseTotal, visible: Boolean) {
        binding?.expenseTotal?.apply {
            total.text = if (visible) expenseTotal.total.formatCurrency()
            else expenseTotal.total.formatCurrencyHidden()

            amountDiff.text = if (visible) expenseTotal.diffLastMonth.formatCurrency()
            else expenseTotal.diffLastMonth.formatCurrencyHidden()

            toggleVisibility.isChecked = visible
        }
    }
}