//package com.dedy.thematisation.ui.start
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.ThumbUp
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import com.dedy.thematisation.data.PostRepo
//
//@Composable
//fun Home(){
//    val featured = remember { PostRepo.getFeaturedPost() }
//    val posts = remember { PostRepo.getPosts() }
//
//    MaterialTheme {
//        Scaffold(
//            topBar = {  }
//        ) { innerPadding ->
//            LazyColumn(content = innerPadding){
//                item {
//                    Header
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun AppBar() {
//    TopAppBar(
//        navigationIcon = {
//            Icon(
//                imageVector = Icons.Rounded.ThumbUp,
//                contentDescription = null,
//                modifier = Modifier.padding(horizontal = 12.dp)
//            )
//        },
//        title = {
//            Text(text = stringResource(R.string.app_title))
//        },
//        backgroundColor = MaterialTheme.colors.primary
//    )
//}