package com.example.planify.main.features.actions.data.repositories_impl

import com.example.planify.main.features.actions.data.dto.get_my_incoming_actions.GetMyIncomingActionsDTO
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.repositories.ActionsRepository
import com.example.planify.main.features.actions.domain.utils.ActionDataParser
import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import io.ktor.client.plugins.timeout
import io.ktor.http.HttpMethod
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActionsRepositoryImpl @Inject constructor(
    private val authenticatedApiClient: AuthenticatedApiClient,
    private val actionDataParser: ActionDataParser
) : ActionsRepository {
    private val actionsFeaturePath = "/actions"

    private val getMyIncomingActionsPath = "$actionsFeaturePath/my/incoming"

    override suspend fun fetchActions(): Result<List<Action<*>>> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val response = authenticatedApiClient.requestNotNull<GetMyIncomingActionsDTO> {
                method = HttpMethod.Get
                url { path(getMyIncomingActionsPath) }
                timeout {
                    requestTimeoutMillis = 60000
                    connectTimeoutMillis = 60000
                    socketTimeoutMillis = 60000
                }
            }

            response.actions.map { it.toEntity(actionDataParser) }
        }
    }
}
