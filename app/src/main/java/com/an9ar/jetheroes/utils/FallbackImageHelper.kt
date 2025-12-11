package com.an9ar.jetheroes.utils

object FallbackImageHelper {
    // List of publicly accessible Marvel hero images as fallbacks
    private val fallbackMarvelImages = listOf(
        "https://upload.wikimedia.org/wikipedia/en/a/aa/Hulk_%28circa_2019%29.png", // Iron Man
        "https://upload.wikimedia.org/wikipedia/en/b/bf/CaptainAmericaHughes.jpg", // Captain America
        "https://upload.wikimedia.org/wikipedia/en/thumb/1/17/Thor_by_Olivier_Coipel.png/250px-Thor_by_Olivier_Coipel.png", // Thor
        "https://upload.wikimedia.org/wikipedia/en/a/aa/Hulk_%28circa_2019%29.png", // Hulk
        "https://static.wikia.nocookie.net/marvelcinematicuniverse/images/3/3f/Black_Widow_Infobox.jpg/revision/latest/scale-to-width-down/1200?cb=20231025163748", // Black Widow
    )
    
    /**
     * Gets a random fallback Marvel hero image URL
     */
    fun getRandomFallbackImage(): String {
        return fallbackMarvelImages.random()
    }
    
    /**
     * Gets a fallback image based on hero name (for consistency)
     */
    fun getFallbackImageForHero(heroName: String): String {
        // Use hero name hash to consistently select same fallback for same hero
        val index = heroName.hashCode().absoluteValue() % fallbackMarvelImages.size
        return fallbackMarvelImages[index]
    }
    
    private fun Int.absoluteValue(): Int = if (this < 0) -this else this
}

