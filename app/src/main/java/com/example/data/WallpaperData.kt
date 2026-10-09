package com.example.data

import androidx.compose.ui.graphics.Color

data class WallpaperItem(
    val id: Int,
    val name: String,
    val url: String,
    val gradientColors: List<Color>
)

object WallpaperData {
    val items = listOf(
        WallpaperItem(
            id = 0,
            name = "Floral Bokeh",
            url = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF2C3E50), Color(0xFF3498DB), Color(0xFFE74C3C))
        ),
        WallpaperItem(
            id = 1,
            name = "Sunbeams in Forest",
            url = "https://images.unsplash.com/photo-1513836279014-a89f7a76ae86?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF134E5E), Color(0xFF71B280))
        ),
        WallpaperItem(
            id = 2,
            name = "Teal Grunge",
            url = "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
        ),
        WallpaperItem(
            id = 3,
            name = "Geometric Pattern",
            url = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF654ea3), Color(0xFFeaafc8))
        ),
        WallpaperItem(
            id = 4,
            name = "Red Sunburst",
            url = "https://images.unsplash.com/photo-1550684848-fac1c5b4e853?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFFe52d27), Color(0xFFb31217))
        ),
        WallpaperItem(
            id = 5,
            name = "Purple Sunset",
            url = "https://images.unsplash.com/photo-1579546929662-711aa81148cf?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFFcc2b5e), Color(0xFF753a88))
        ),
        WallpaperItem(
            id = 6,
            name = "Soft Beach Ocean",
            url = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
        ),
        WallpaperItem(
            id = 7,
            name = "Pastel Pink Bokeh",
            url = "https://images.unsplash.com/photo-1522383225653-ed111181a951?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFFee9ca7), Color(0xFFffdde1))
        ),
        WallpaperItem(
            id = 8,
            name = "Deep Space Galaxy",
            url = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF000428), Color(0xFF004e92))
        ),
        WallpaperItem(
            id = 9,
            name = "Misty Mountain Peaks",
            url = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF3E5151), Color(0xFFDECBA4))
        ),
        WallpaperItem(
            id = 10,
            name = "Cyberpunk Neon",
            url = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF8A2387), Color(0xFFE94057), Color(0xFFF27121))
        ),
        WallpaperItem(
            id = 11,
            name = "Mountain Valley River",
            url = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF11998e), Color(0xFF38ef7d))
        ),
        WallpaperItem(
            id = 12,
            name = "Aurora Northern Lights",
            url = "https://images.unsplash.com/photo-1534447677768-be436bb09401?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF0575E6), Color(0xFF00F260))
        ),
        WallpaperItem(
            id = 13,
            name = "Starry Night Sky",
            url = "https://images.unsplash.com/photo-1519681393784-d120267933ba?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF141E30), Color(0xFF243B55))
        ),
        WallpaperItem(
            id = 14,
            name = "Golden Sunlight Leaves",
            url = "https://images.unsplash.com/photo-1518495973542-4542c06a5843?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFFf12711), Color(0xFFf5af19))
        ),
        WallpaperItem(
            id = 15,
            name = "Minimal Green Leaves",
            url = "https://images.unsplash.com/photo-1502082553048-f009c37129b9?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF1D976C), Color(0xFF93F9B9))
        ),
        WallpaperItem(
            id = 16,
            name = "Nature Fog Morning",
            url = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF757F9A), Color(0xFFD7DDE8))
        ),
        WallpaperItem(
            id = 17,
            name = "Cherry Blossom Sakura",
            url = "https://images.unsplash.com/photo-1516617442634-75371039cb3a?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFFff9a9e), Color(0xFFfecfef))
        ),
        WallpaperItem(
            id = 18,
            name = "Blue Calm Ocean Wave",
            url = "https://images.unsplash.com/photo-1518837695005-2083093ee35b?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFF4facfe), Color(0xFF00f2fe))
        ),
        WallpaperItem(
            id = 19,
            name = "Minimalist Desert Dunes",
            url = "https://images.unsplash.com/photo-1531685250784-7569952593d2?q=75&w=900&auto=format&fit=crop",
            gradientColors = listOf(Color(0xFFf857a6), Color(0xFFff5858))
        )
    )
}
