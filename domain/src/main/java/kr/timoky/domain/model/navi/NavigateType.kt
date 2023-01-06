package kr.timoky.domain.model.navi

sealed class NavigateType {
    abstract val getItem: BottomNavItem?
    abstract val fragmentName: String

    class List(private val navItem: BottomNavItem) : NavigateType() {
        override val getItem: BottomNavItem
            get() = navItem
        override val fragmentName: String = "ListFragment"
    }

    class Calendar(private val navItem: BottomNavItem) : NavigateType() {
        override val getItem: BottomNavItem
            get() = navItem
        override val fragmentName: String = "CalendarFragment"
    }

    class Chart(private val navItem: BottomNavItem) : NavigateType() {
        override val getItem: BottomNavItem
            get() = navItem
        override val fragmentName: String = "ChartFragment"
    }

    class Setting(private val navItem: BottomNavItem) : NavigateType() {
        override val getItem: BottomNavItem
            get() = navItem
        override val fragmentName: String = "SettingFragment"
    }

    class AddExpense(private val navItem: BottomNavItem? = null) : NavigateType() {
        override val getItem: BottomNavItem?
            get() = navItem
        override val fragmentName: String = "AddExpenseFragment"
    }

}