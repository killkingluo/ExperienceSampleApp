package com.example.experiencesampleapp.function

import com.example.experiencesampleapp.entity.PhishingMessage
import com.example.experiencesampleapp.repository.PhishingMessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class InsertTestData @Inject constructor(private val phishingMessageRepository: PhishingMessageRepository) {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun insertPhishingMessage() {
        ioScope.launch {
            phishingMessageRepository.insertPhishingMessage(
                PhishingMessage(
                    message = "Hello, I am an Amazon company promoter and you can participate in my exclusive event to enjoy a 50% discount~\n" +
                            "Click on the link below to view:\n" +
                            "https://www.annazon.co.uk/discount/56392",
                    usedFlag = 0
                )
            )
            phishingMessageRepository.insertPhishingMessage(
                PhishingMessage(
                    message = "Hello, I am a Microsoft recruiter who can recommend jobs to you internally. You can click on the following link to view job opportunities.\n" +
                            "https://careers.nnicrosoft.com/v2/global/en/offer/5423",
                    usedFlag = 0
                )
            )
            phishingMessageRepository.insertPhishingMessage(
                PhishingMessage(
                    message = "Hello, I'm the auditor of the Bank of Scotland. We found that you have assisted in money laundering in our bank. Please register your personal information on the following website as soon as possible to assist us in our investigation, otherwise you will face charges.\n" +
                            "https://auditor.bankofsc0tIand.co.uk/register",
                    usedFlag = 0
                )
            )
            phishingMessageRepository.insertPhishingMessage(
                PhishingMessage(
                    message = "Hello, I am the secretary of the college. Please check this document regarding your review of last semester's grades.",
                    usedFlag = 0
                )
            )
        }
    }
}