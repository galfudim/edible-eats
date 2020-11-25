package courses.projects.edible_eats

class MenuChoice {
    var restaurantName: String? = null
    var diet: String? = null
    var choiceName: String? = null
    var preferences: ArrayList<String>? = null

    constructor(restaurantName: String, diet: String, choiceName: String, preferences: ArrayList<String>) {
        this.restaurantName = restaurantName
        this.diet = diet
        this.choiceName = choiceName
        this.preferences = preferences
    }
}