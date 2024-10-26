package com.gestvet.gestvet.domain.appointments

import com.gestvet.gestvet.data.network.AppointmentsService
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppointmentsUseCase @Inject constructor(private val appointmentsService: AppointmentsService) {
    operator fun invoke(): Flow<QuerySnapshot> = appointmentsService.appointments()

    fun getAllAppointments() = appointmentsService.getAllAppointments()
}