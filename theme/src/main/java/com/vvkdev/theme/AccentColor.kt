import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import com.vvkdev.theme.R

enum class AccentColor(@StyleRes val themeRes: Int, @ColorRes val colorRes: Int) {
    BLUE(R.style.ThemeBlue, R.color.blue_primary),
    LILAC(R.style.ThemeLilac, R.color.lilac_primary),
    RED(R.style.ThemeRed, R.color.red_primary),
    ORANGE(R.style.ThemeOrange, R.color.orange_primary),
    GREEN(R.style.ThemeGreen, R.color.green_primary),
    YELLOW(R.style.ThemeYellow, R.color.yellow_primary),
    BEIGE(R.style.ThemeBeige, R.color.beige_primary),
    GREY(R.style.ThemeGrey, R.color.grey_primary);

    companion object {
        fun fromName(name: String): AccentColor = entries.first { it.name == name }
    }
}
