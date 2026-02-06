package com.example.planify.main.features.actions.data.sources

import com.example.planify.main.features.actions.data.dto.ActionDTO
import com.example.planify.main.features.actions.data.dto.get_my_incoming_actions.GetMyIncomingActionsDTO
import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import io.ktor.client.plugins.timeout
import io.ktor.http.HttpMethod
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActionsRemoteDataSource @Inject constructor(
    private val authenticatedApiClient: AuthenticatedApiClient
) {
    private val actionsFeaturePath = "/actions"

    private val getMyIncomingActionsPath = "$actionsFeaturePath/my/incoming"
    private val patchActionPath = "$actionsFeaturePath/%s/checked"

    suspend fun fetchActions(): Result<List<ActionDTO>> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val response = authenticatedApiClient.requestNotNull<GetMyIncomingActionsDTO> {
                method = HttpMethod.Get
                url { path(getMyIncomingActionsPath) }
                timeout {  // TODO: To meta info
                    requestTimeoutMillis = 60000
                    connectTimeoutMillis = 60000
                    socketTimeoutMillis = 60000
                }
            }

            response.actions
        }
    }

    suspend fun deleteAction(actionId: String): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            authenticatedApiClient.requestUnit {
                method = HttpMethod.Patch
                url { path(patchActionPath.format(actionId)) }
            }
        }
    }
}
