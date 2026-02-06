package com.example.planify.main.features.actions.data.sources

import com.example.planify.core.data.serializers.jsonCore
import com.example.planify.main.features.actions.data.dao.ActionModelDAO
import com.example.planify.main.features.actions.data.models.ActionModel
import com.example.planify.main.features.actions.domain.entities.Action
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActionsLocalDataSource @Inject constructor(
    private val actionModelDAO: ActionModelDAO
) {
    suspend fun <T : Any> saveAction(action: Action<T>, serializer: KSerializer<T>): Result<ActionModel> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val model = ActionModel(
                id = action.id,
                checked = action.checked,
                type = action.type,
                data = action.data?.let { jsonCore.encodeToString(serializer, action.data) }
            )

            actionModelDAO.insert(model)

            model
        }
    }

    suspend fun getAllActions(): Result<List<ActionModel>> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            actionModelDAO.getAll()
        }
    }

    suspend fun deleteAction(actionId: String): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            actionModelDAO.deleteActionById(actionId)
        }
    }
}
