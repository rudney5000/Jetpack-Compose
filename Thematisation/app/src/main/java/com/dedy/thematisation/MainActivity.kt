package com.dedy.thematisation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dedy.thematisation.data.Post
import com.dedy.thematisation.data.PostRepo
import com.dedy.thematisation.ui.finish.theme.ThematisationTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThematisationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home(){
    val featured = remember { PostRepo.getFeaturedPost() }
    val posts = remember { PostRepo.getPosts() }

    MaterialTheme {
        Scaffold(
            topBar = { AppBar() }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding){
                item {
                    Header(stringResource(id = R.string.top))
                }
                item { 
                    FeaturedPost(
                        post = featured,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    Header(stringResource(id = R.string.popular))
                }
                items(posts) { post ->
                    PostItem(post = post)
                    Divider(startIndent = 72.dp)

                }
            }
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        navigationIcon = {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_launcher),
//                contentDescription = null,
//                modifier = Modifier.padding(horizontal = 12.dp)
//            )
            Icon(
                imageVector = Icons.Rounded.ThumbUp,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        title = {
            Text(text = stringResource(R.string.app_title))
        },
        backgroundColor = MaterialTheme.colors.primarySurface
    )
}

@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        contentColor = MaterialTheme.colors.primary,
        modifier = Modifier.semantics { heading() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle2,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
//    Text(
//        text = text,
//        modifier = modifier
//            .fillMaxWidth()
//            .background(Color.LightGray)
//            .semantics { heading() }
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//    )
}

@Composable
fun FeaturedPost(
    post: Post,
    modifier: Modifier = Modifier
){
    Card(modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { }
        ) {
            Image(
                painter = painterResource(id = post.imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .heightIn(min = 180.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            val padding = Modifier.padding(horizontal = 16.dp)
            Text(
                text = post.title,
                style = MaterialTheme.typography.h6,
                modifier = padding
            )
            Text(
                text = post.metadata.author.name,
                style = MaterialTheme.typography.body2,
                modifier = padding
            )
            PostMetadata(post, padding)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PostMetadata(
    post: Post,
    modifier: Modifier = Modifier
){
    val divider = "  •  "
    val tagDivider = "  "
    val text = buildAnnotatedString { 
        append(post.metadata.date)
        append(divider)
        append(stringResource(id = R.string.read_time, post.metadata.readTimeMinutes))
        append(divider)
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        )
        post.tags.forEachIndexed { index, tag -> 
            if (index != 0){
                append(tagDivider)
            }
            withStyle(tagStyle){
                append(" ${tag.uppercase(Locale.getDefault())}")
            }
        }
    }
    CompositionLocalProvider( LocalContentAlpha provides  ContentAlpha.medium) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = modifier
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
){
    ListItem(
        modifier = modifier
            .clickable { }
            .padding(vertical = 8.dp),
        icon = {
            Image(
                painter = painterResource(id = post.imageThumbId),
                contentDescription = null,
                modifier = Modifier.clip(shape = MaterialTheme.shapes.small)
            )
        },
        text = {
            Text(text = post.title)
        },
        secondaryText = {
            PostMetadata(post = post)
        }
    )
}

@Preview("Post Item")
@Composable
private fun PostItemPreview(){
    val post = remember { PostRepo.getFeaturedPost() }
    ThematisationTheme() {
        PostItem(post = post)
    }
}

@Preview("Featured Post")
@Composable
fun FeaturedPostPreview(){
    val post = remember{ PostRepo.getFeaturedPost() }
    ThematisationTheme {
        FeaturedPost(post = post)
    }
}

@Preview("Featured Post . Dark")
@Composable
fun FeaturedPostDarkPreview(){
    val post = remember{ PostRepo.getFeaturedPost() }
    ThematisationTheme(darkTheme = true) {
        FeaturedPost(post = post)
    }
}

@Preview("Home")
@Composable
private fun HomePreview(){
    Home()
}