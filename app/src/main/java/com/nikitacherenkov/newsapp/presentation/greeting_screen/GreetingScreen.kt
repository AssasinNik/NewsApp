// Необходимые импорты
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nikitacherenkov.newsapp.presentation.reusable_components.ShimmerImage
import com.nikitacherenkov.newsapp.presentation.ui.theme.Poppins
import com.nikitacherenkov.newsapp.utils.Constants.DEFAULT_IMAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

data class HeadlineData(
    val text: String,
    val offset: Offset,
    val rotation: Float
)
@Composable
fun SequentialHeadlinesStage(onFinish: () -> Unit) {
    val headlines = listOf(
        HeadlineData("Срочно!", Offset(30f, 50f), -10f),
        HeadlineData("Важное!", Offset(230f, 600f), 15f),
        HeadlineData("Эксклюзив!", Offset(50f, 400f), 5f),
        HeadlineData("Сенсация!", Offset(200f, 100f), -5f)
    )
    var currentIndex by remember { mutableStateOf(-1) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        for (i in headlines.indices) {
            currentIndex = i
            alphaAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
            )
            delay(400)
            alphaAnim.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
            )
            delay(200)
        }
        onFinish()
    }

    if (currentIndex in headlines.indices) {
        val headline = headlines[currentIndex]
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = headline.text,
                fontSize = 50.sp,
                color = Color.Black,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .offset {
                        IntOffset(
                            headline.offset.x.dp.roundToPx(),
                            headline.offset.y.dp.roundToPx()
                        )
                    }
                    .rotate(headline.rotation)
                    .alpha(alphaAnim.value)
            )
        }
    }
}
@Composable
fun StagedLoginScreen(onGoogleLoginClicked: () -> Unit) {
    var stage by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (stage) {
            1 -> {
                SequentialHeadlinesStage(onFinish = { stage = 2 })
            }
            2 -> {
                FinalStage(onGoogleLoginClicked)
            }
        }
    }
}

@Composable
fun FinalStage(onGoogleLoginClicked: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(500)
        visible = true
    }

    AnimatedVisibility(visible = visible) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(DEFAULT_IMAGE)
                        .dispatcher(Dispatchers.IO)
                        .memoryCacheKey(DEFAULT_IMAGE)
                        .diskCacheKey(DEFAULT_IMAGE)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = DEFAULT_IMAGE,
                    contentScale = ContentScale.Crop,
                    filterQuality = FilterQuality.None,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )
                Text(
                    text = "Приступим к изучению новостей",
                    fontSize = 24.sp,
                    color = Color.Black
                )
                Button(
                    onClick = onGoogleLoginClicked,
                    modifier = Modifier.size(width = 250.dp, height = 56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Войти с Google",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
