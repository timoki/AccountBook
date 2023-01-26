package kr.timoky.data.repository.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kr.timoky.data.mapper.ObjectMapper.toEntity
import kr.timoky.data.mapper.ObjectMapper.toModel
import kr.timoky.data.repository.dataSource.LocalDataSource
import kr.timoky.data.repository.dataSource.impl.CategoryPagingDataSource
import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.model.Result
import kr.timoky.domain.repository.CategoryRepository
import kr.timoky.domain.utils.ErrorCallback
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val errorCallback: ErrorCallback
) : CategoryRepository {
    override fun getCategoryList(): Flow<PagingData<CategoryModel>> = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            CategoryPagingDataSource(
                localDataSource,
                errorCallback
            )
        }
    ).flow

    override fun getCategory(id: Int): Flow<Result<CategoryModel?>> {

        return flow<Result<CategoryModel?>> {
            val response = localDataSource.getCategory(id)
            emit(
                Result.Success(
                    response?.toModel()
                )
            )
        }.catch { exception ->
            emit(Result.Error(exception.message))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertCategory(item: CategoryModel) = withContext(Dispatchers.IO) {
        localDataSource.insertCategory(item.toEntity())
    }

    override suspend fun deleteCategory(item: CategoryModel) = withContext(Dispatchers.IO) {
        localDataSource.deleteCategory(item.toEntity())
    }
}