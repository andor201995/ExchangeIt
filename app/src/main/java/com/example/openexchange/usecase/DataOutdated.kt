package com.example.openexchange.usecase

interface DataOutdated {
    suspend operator fun invoke(): Boolean
}
