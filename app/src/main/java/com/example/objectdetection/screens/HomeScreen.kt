package com.example.objectdetection.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.objectdetection.MainViewModel
import com.example.objectdetection.insects.InsectList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    val viewModel = viewModel<MainViewModel>()
    val query by viewModel.query.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val insects by viewModel.insects.collectAsState();
    var isActive by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
//        TextField(value = query,
//            onValueChange = viewModel::onSearchTextChange,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp, 0.dp),
//            placeholder = { Text(text = "Search insect here...") },
//            leadingIcon = {

//            }
//
//        )
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChange = viewModel::onSearchTextChange,
            onSearch = {
                isActive = false
            },
            active = isActive,
            onActiveChange = {
                isActive = it
            },
            placeholder = {
                Text(text = "Search insect here.")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search icon"
                )
            },
            trailingIcon = {
                if (isActive) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) {
                                viewModel.onSearchTextChange("")
                            } else {
                                isActive = false
                            }

                        },
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close icon"
                    )
                }

            }
        ) {}
        Spacer(modifier = Modifier.height(16.dp))
        InsectList(insects)
    }

}


