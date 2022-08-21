package com.example.mealplanner.ui.grocerylist.viewmodels

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.model.GroceryItem
import com.example.mealplanner.data.model.GroceryItemStatus
import com.example.mealplanner.data.repo.GroceryListRepository
import com.example.mealplanner.data.repo.HouseholdRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class GroceryListViewModelTest {
    val groceryRepo: GroceryListRepository = mock()
    val householdRepo: HouseholdRepository = mock()
    val application: Application = mock()

    val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_deletion() = runTest {
        val groceryItem = GroceryItem(1,"testItem", 5F, "gram")

        Mockito.`when`(groceryRepo.deleteGroceryItem(groceryItem)).thenReturn(Unit)
        Mockito.`when`(groceryRepo.groceryList).thenReturn(MutableLiveData(listOf(groceryItem)))

        val viewModel = GroceryListViewModel(groceryRepo, householdRepo, application)
        launch {viewModel.removeGroceryItem(0)}
        advanceUntilIdle()

        Mockito.verify(groceryRepo, times(1)).deleteGroceryItem(groceryItem)
    }

    @Test
    fun `Update item to scrapped, no same ingredient exist that is already scrapped`() = runTest {
        val groceryItem = GroceryItem(1,"testItem", 5F, "gram")
        val groceryItemChecked = GroceryItem(1, "testItem", 5F, "gram", GroceryItemStatus.SCRAPPED)

        val viewModel = GroceryListViewModel(groceryRepo, householdRepo, application)
        Mockito.`when`(groceryRepo.groceryList).thenReturn(MutableLiveData(listOf(groceryItem)))
        Mockito.`when`(groceryRepo.updateGroceryItem(groceryItemChecked)).thenReturn(Unit)

        launch { viewModel.groceryItemChecked(0) }
        advanceUntilIdle()

        Mockito.verify(groceryRepo, times(1)).updateGroceryItem(groceryItemChecked)
    }

    @Test
    fun `Update item to scrapped, a scrapped version of ingredient already exists`() = runTest {
        val groceryItem = GroceryItem(1,"testItem", 5F, "gram")
        val groceryItemChecked = GroceryItem(1, "testItem", 5F, "gram", GroceryItemStatus.SCRAPPED)
        val combinedItem = GroceryItem(1, "testItem", 10F, "gram", GroceryItemStatus.SCRAPPED)

        val viewModel = GroceryListViewModel(groceryRepo, householdRepo, application)
        Mockito.`when`(groceryRepo.groceryList).thenReturn(MutableLiveData(listOf(groceryItem)))
        Mockito.`when`(groceryRepo.updateGroceryItem(combinedItem))
            .thenReturn(Unit)
        Mockito.`when`(groceryRepo.updateGroceryItem(groceryItemChecked))
            .thenThrow(SQLiteConstraintException())
            .thenReturn(Unit)
        Mockito.`when`(groceryRepo.findOtherBySpoonIdAndStatus(groceryItem)).thenReturn(combinedItem)

        Mockito.`when`(groceryRepo.deleteGroceryItem(groceryItem)).thenReturn(Unit)

        launch { viewModel.groceryItemChecked(0) }
        advanceUntilIdle()

        Mockito.verify(groceryRepo, times(2)).updateGroceryItem(combinedItem)
        Mockito.verify(groceryRepo, times(1)).deleteGroceryItem(groceryItem)
    }
}