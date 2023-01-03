package kr.timoky.domain.usecase

import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(
        item: CategoryModel
    ) {
        categoryRepository.deleteCategory(item)
    }
}