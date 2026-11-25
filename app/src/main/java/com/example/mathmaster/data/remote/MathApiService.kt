
package com.example.mathmaster.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MathApiService {

    @GET("api.php")
    suspend fun getMathematicsQuestions(
        @Query("amount") amount: Int = 10,
        @Query("category") category: Int = 19,
        @Query("type") type: String = "multiple"
    ): MathApiResponse
}
