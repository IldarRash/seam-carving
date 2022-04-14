fun containsKeyAndValue(map: Map<String, String>, value: String): Boolean =
    map.containsValue(value) && map.containsKey(value)
