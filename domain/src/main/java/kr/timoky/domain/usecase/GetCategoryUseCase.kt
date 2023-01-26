package kr.timoky.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.model.Result
import kr.timoky.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(
        id: Int
    ): Flow<Result<CategoryModel?>> {
        return categoryRepository.getCategory(id)
    }
}