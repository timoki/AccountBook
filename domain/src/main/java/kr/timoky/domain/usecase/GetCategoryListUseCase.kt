package kr.timoky.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<PagingData<CategoryModel>> {
        return categoryRepository.getCategoryList()
    }
}