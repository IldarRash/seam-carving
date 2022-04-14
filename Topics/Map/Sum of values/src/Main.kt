fun summator(map: Map<Int, Int>): Int =
    map.filter { it.key % 2 == 0 }.values
        .sum()
