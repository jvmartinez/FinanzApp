package com.jvmartinez.finanzapp.core.repository.remote.balance

import com.jvmartinez.finanzapp.core.api.ResponseBasic
import com.jvmartinez.finanzapp.core.model.Balance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface IRepositoryBalance {
    suspend fun save(balance: Balance): Flow<ResponseBasic<Balance>>
    suspend fun getBalance(): Flow<ResponseBasic<Balance>>
    suspend fun updateBalance(balance: Balance): Flow<ResponseBasic<Balance>>
    suspend fun deleteBalance(id: String):  Flow<ResponseBasic<Boolean>>
}
@Singleton
class RepositoryBalance @Inject constructor(
    private val api: BalanceService,
): IRepositoryBalance {

    override suspend fun save(balance: Balance): Flow<ResponseBasic<Balance>> {
        return flow { emit(api.save(balance)) }
    }

    override suspend fun getBalance(): Flow<ResponseBasic<Balance>> {
        return flow { emit(api.findByUserId()) }
    }

    override suspend fun updateBalance(balance: Balance): Flow<ResponseBasic<Balance>> {
        return flow { emit(api.update(balance)) }
    }

    override suspend fun deleteBalance(id: String): Flow<ResponseBasic<Boolean>> {
        return flow { emit(api.delete(id)) }
    }

}
