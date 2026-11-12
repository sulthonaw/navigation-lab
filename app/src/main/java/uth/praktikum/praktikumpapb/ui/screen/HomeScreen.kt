package uth.praktikum.praktikumpapb.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uth.praktikum.praktikumpapb.ui.home.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onStudentClick: (Int) -> Unit,
    onOpenProfile: () -> Unit,
    onOpenAbout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Daftar Mahasiswa", style = MaterialTheme.typography.headlineMedium)

        // Kolom Pencarian Reaktif & Tombol Refresh (Tugas Praktikum No. 7)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Cari profil") },
                placeholder = { Text("Ketik nama...") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Button(onClick = onRefresh) {
                Text("Refresh")
            }
        }

        // Tampilan Kondisi Loading, Error, dan Data List
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = onRetry,
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            Text("Coba Lagi")
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.students) { student ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onStudentClick(student.id) }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "${student.id} - ${student.name}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Email: ${student.email}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Univ: ${student.university}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onOpenProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buka Profile")
        }

        OutlinedButton(
            onClick = onOpenAbout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buka About")
        }
    }
}
