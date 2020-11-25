package courses.projects.edible_eats

 class MenuItem {
    var restaurantName: String? = null
     var dietPreference: String? = null
     var menuItemsList: ArrayList<String>? = null

    fun constructor(restaurantName: String?, dietPreference: String? , itemName: String?) {
        this.restaurantName = restaurantName
        this.dietPreference = dietPreference
        menuItemsList!!.add(itemName!!)

    }

     fun containsItem(item: String?): Boolean {
         if(menuItemsList!!.contains(item))
             return true
         else
             return false
     }


}