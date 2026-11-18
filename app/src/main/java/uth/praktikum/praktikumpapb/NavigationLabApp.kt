package uth.praktikum.praktikumpapb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uth.praktikum.praktikumpapb.data.local.DatabaseProvider
import uth.praktikum.praktikumpapb.data.preferences.UserPreferencesRepository
import uth.praktikum.praktikumpapb.data.remote.RetrofitClient
import uth.praktikum.praktikumpapb.data.remote.StudentRemoteDataSource
import uth.praktikum.praktikumpapb.data.repository.DefaultStudentRepository
import uth.praktikum.praktikumpapb.data.repository.FavoriteRepository
import uth.praktikum.praktikumpapb.ui.detail.DetailViewModel
import uth.praktikum.praktikumpapb.ui.favorites.FavoritesViewModel
import uth.praktikum.praktikumpapb.ui.home.HomeViewModel
import uth.praktikum.praktikumpapb.ui.screen.AboutScreen
import uth.praktikum.praktikumpapb.ui.screen.DetailScreen
import uth.praktikum.praktikumpapb.ui.screen.FavoritesScreen
import uth.praktikum.praktikumpapb.ui.screen.HomeScreen
import uth.praktikum.praktikumpapb.ui.screen.ProfileScreen

@Composable
fun NavigationLabApp() {
    val remoteDataSource = remember {
        StudentRemoteDataSource(RetrofitClient.apiService)
    }
    val repository = remember {
        DefaultStudentRepository(remoteDataSource)
    }

    val context = LocalContext.current

    val userPreferencesRepository = remember { UserPreferencesRepository(context) }

    val favoriteRepository = remember {
        FavoriteRepository(DatabaseProvider.getDatabase(context).favoriteStudentDao())
    }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.factory(repository, userPreferencesRepository)
            )
            val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
            val searchQuery by homeViewModel.searchQuery.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                searchQuery = searchQuery,
                onSearchQueryChange = homeViewModel::onSearchQueryChange,
                onRefresh = homeViewModel::refresh,
                onRetry = homeViewModel::retry,
                onStudentClick = { id ->
                    navController.navigate(Routes.detail(id))
                },
                onOpenFavorites = {
                    navController.navigate(Routes.FAVORITES)
                },
                onOpenProfile = {
                    navController.navigate(Routes.PROFILE)
                },
                onOpenAbout = {
                    navController.navigate(Routes.ABOUT)
                }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument("studentId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: -1
            val detailViewModel: DetailViewModel = viewModel(
                factory = DetailViewModel.factory(repository, favoriteRepository)
            )
            LaunchedEffect(studentId) {
                detailViewModel.loadStudent(studentId)
            }
            val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()

            DetailScreen(
                uiState = uiState,
                onBack = { navController.popBackStack() },
                onToggleFavorite = { detailViewModel.toggleFavorite() }
            )
        }
        composable(Routes.FAVORITES) {
            val favoritesViewModel: FavoritesViewModel = viewModel(
                factory = FavoritesViewModel.factory(favoriteRepository)
            )
            val uiState by favoritesViewModel.uiState.collectAsStateWithLifecycle()

            FavoritesScreen(
                uiState = uiState,
                onStudentClick = { id ->
                    navController.navigate(Routes.detail(id))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ABOUT) {
            AboutScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
