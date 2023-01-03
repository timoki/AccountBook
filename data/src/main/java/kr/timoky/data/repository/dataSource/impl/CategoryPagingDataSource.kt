package kr.timoky.data.repository.dataSource.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.timoky.data.mapper.ObjectMapper.toCategoryModel
import kr.timoky.data.repository.dataSource.LocalDataSource
import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.utils.ErrorCallback

class CategoryPagingDataSource(
    private val localDataSource: LocalDataSource,
    private val errorCallback: ErrorCallback
) : PagingSource<Int, CategoryModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryModel> {
        val page = params.key ?: 1
        return try {
            val response = withContext(Dispatchers.IO) {
                localDataSource.getCategoryList()
            }

            LoadResult.Page(
                data = response.toCategoryModel(),
                prevKey = page % 2 - 1,
                nextKey = page % 2 + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CategoryModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}