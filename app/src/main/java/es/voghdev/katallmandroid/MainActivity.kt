package es.voghdev.katallmandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.voghdev.katallmandroid.features.llm.data.LLM
import es.voghdev.katallmandroid.features.llm.ui.HomeUiState
import es.voghdev.katallmandroid.features.llm.ui.HomeViewModel
import es.voghdev.katallmandroid.navigation.AppNavigation
import es.voghdev.katallmandroid.ui.theme.KataLLMAndroidTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KataLLMAndroidTheme {
                AppNavigation { onNavigateToProfile, onNavigateToDetail ->
                    HomeScreen(
                        onNavigateToProfile = onNavigateToProfile,
                        onNavigateToDetail = onNavigateToDetail,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToDetail: (Int) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("KataLLM") },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        },
    ) { innerPadding ->
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(innerPadding).padding(16.dp),
                )
            }
            is HomeUiState.Success -> {
                LLMList(
                    llms = state.llms,
                    onItemClick = onNavigateToDetail,
                    modifier = Modifier.padding(innerPadding),
                )
            }
            is HomeUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(innerPadding).padding(16.dp),
                )
            }
        }
    }
}

@Composable
fun LLMList(
    llms: List<LLM>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(llms) { index, llm ->
            LLMListItem(
                llm = llm,
                onClick = { onItemClick(index) },
            )
            if (index < llms.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun LLMListItem(
    llm: LLM,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Text(
            text = llm.name,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = llm.company,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    KataLLMAndroidTheme {
        LLMList(
            llms = listOf(
                LLM("GPT-4o", "OpenAI", "May 2024", "Multimodal flagship model."),
                LLM("Claude 3.5 Sonnet", "Anthropic", "June 2024", "High-performance model."),
            ),
            onItemClick = {},
        )
    }
}
