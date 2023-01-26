package kr.timoky.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.model.Result

interface CategoryRepository {
    fun getCategoryList(): Flow<PagingData<CategoryModel>>

    fun getCategory(id: Int): Flow<Result<CategoryModel?>>

    suspend fun insertCategory(item: CategoryModel)

    suspend fun deleteCategory(item: CategoryModel)
}