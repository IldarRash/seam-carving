

fun bill(priceList: Map<String, Int>, shoppingList: MutableList<String>): Int =
    shoppingList.map{ priceList[it]}.sumOf { it?: 0 }
