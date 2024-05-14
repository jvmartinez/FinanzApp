package com.jvmartinez.finanzapp.core.repository.remote.balance

import com.jvmartinez.finanzapp.core.api.ResponseBasic
import com.jvmartinez.finanzapp.core.model.Balance
import com.jvmartinez.finanzapp.core.model.Transaction
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface BalanceService {

    @GET("balance/findAll")
    suspend fun findAll(): ResponseBasic<List<Balance>>

    @GET("/balance/findByUser")
    suspend fun findByUserId(): ResponseBasic<Balance>

    @POST("/newBalance/add")
    suspend fun save(@Body balance: Balance): ResponseBasic<Balance>

    @PUT("/balance/update")
    suspend fun update(@Body balance: Balance): ResponseBasic<Balance>

    @DELETE("/balance/delete")
    suspend fun delete(id: String): ResponseBasic<Boolean>

    @POST("/newTransaction/add")
    suspend fun createTransaction(@Body transactions: List<Transaction>): ResponseBasic<List<Transaction>>

    @GET("/transaction/findByUser")
    suspend fun findByUserTransaction(): ResponseBasic<List<Transaction>>
}
