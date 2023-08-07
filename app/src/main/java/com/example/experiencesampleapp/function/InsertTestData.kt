package com.example.experiencesampleapp.function

import com.example.experiencesampleapp.entity.PhishingMessage
import com.example.experiencesampleapp.entity.Question
import com.example.experiencesampleapp.repository.PhishingMessageRepository
import com.example.experiencesampleapp.repository.QuestionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertTestData @Inject constructor(
    private val phishingMessageRepository: PhishingMessageRepository,
    private val questionRepository: QuestionRepository
) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val phishingMessageList = listOf(
        "Hello, I am an Amazon promoter and you can participate in my exclusive event to enjoy a 50% discount~" to "https://www.annazon.co.uk/discount/56392",
        "Hello, I am Tim. I am sharing the school's annual photo album, which contains your photo." to "https://www.faceb0Ok.com/Tim/photos",
        "Hello, I am a Microsoft recruiter who can recommend jobs to you internally. You can click on the following link to view job opportunities." to "https://careers.nnicrosoft.com/v2/global/en/offer/5423",
        "Hello, I'm an auditor of the Bank of Scotland. We found that you have assisted in money laundering. Please register your personal information on the following website as soon as possible to assist us in our investigation, otherwise you will face charges." to "https://auditor.bankofsc0tIand.co.uk/register",
        "Hello, I am the secretary of the college. Please check this document regarding your review of last semester's grades." to "https://inform.universityunion.com/review_login",
        "Your bank card has been frozen, unfreezing requires filling in personal information and submitting it to the bank for verification." to "Https://www.bamkofsc0tland.com/",
        "Hello, the party you registered for needs to fill in your personal information. Click on the link below and fill in the form:" to "https://www.partyworld.com/party?id=1233",
        "Hello, I have a house for rent in the city center. If you are interested, please click on the link below to learn more." to "https://www.zoopla.co.uk/to-rent/details/65255642/",
        "Hello, are you considering buying a house?I have a very beautiful house in the suburbs, please click on the link below for details." to "https://www.rightmove.co.uk/properties/135931085#/?channel=RES_BUY",
        "McDonald's New Offer Coming! Click on the link below to learn more." to "https://www.mcdonalds.com/gb/en-gb/good-to-know/in-our-restaurants/my-mcdonalds-app/rewards.html",
        "Hello, I am an Ebay promoter and you can participate in my exclusive event to enjoy a 50% discount~ Click on the link below to view:" to "https://www.ebav.com/discount/56334",
        "Hello, I am Alis. I am sharing the company's annual photo album, which contains your photo." to "https://www.faceb0Ok.com/Alis/photos",
        "I am working on a city promotion video and hope to receive your likes~" to "https://www.instagram.com/glasgow_city_video/id=1233",
        "Hello, I am a Amazon recruiter who can recommend jobs to you internally. You can click on the following link to view job opportunities." to "https://careers.annazon.com/v2/global/en/offer/5423",
        "KFC's latest discounts offer different discount packages every day~" to "https://www.kfc.co.uk/everyday-deal",
        "Hello, I am a Lidl recruiter who can recommend jobs to you internally. You can click on the following link to view job opportunities." to "https://careers.lidl.co.uk/",
        "Hello, I am a Tesco recruiter who can recommend jobs to you internally. You can click on the following link to view job opportunities." to "https://www.tesco-careers.com/office/corporate-functions/",
        "Hello, I am the secretary of our company. Please check this document regarding your review of last semester's grades." to "https://inform.mycompany.com/review_login",
        "New Burger King event, the new hamburger can enjoy up to 70% off, please click the following link to view." to "https://www.bvrgerkimg.com/rewards/offers",
        "Congratulations on winning the first prize in our weekly lottery. Please click on the following link to view." to "Https://www.everydayhappy124d.com/?id=1",
        "Hello, our company is promoting a new dating and chat app. New users can receive a £ 10 reward by registering. Click on the link below to learn more." to "Https://www.talktoreall.com/register",
        "Congratulations on participating in last night's TV lottery and winning first prize! Please click on the link below to claim it:" to "https://www.lottery123lottery.com/results",
        "Hello, I am the owner of the street corner bar. We will hold a casual party on the weekend. Please click on the link below to learn more:" to "https://www.cornerbar4you.com/party_information",
        "Hello, according to the audit conducted by the tax bureau, you have an income that needs to be taxed. Please click on the link below for details to avoid being accused:" to "https://www.glasgovv.com/index.aspx?articleid=17022345",
        "Hello, I am Mrbeast. I would like to invite you to participate in our video recording event, where you may receive a reward of $100000~ For more information, click on the link below:" to "https://www.mrbeast1234.com",
        "Here is my YouTube homepage:" to "https://www.youtube.com/@MrBeast",
        "Hello, there is an abnormality with your Amazon account. Please click on the link below to learn more:" to "https://www.amazon.co.uk/gp/css/homepage.html?ref_=nav_youraccount_btn",
        "Hello, there is an abnormality with your Ebay account. Please click on the link below to learn more:" to "https://www.ebay.com/mye/myebay",
        "Hello, we offer higher loan amounts and lower interest rates than banks. To learn more, please click on the link below." to "Https://www.loan44yyoouu.com/introduction",
        "I am working on our company promotion video and hope to receive your likes~" to "https://www.instagram.com/xxxcompany_video/id=12333",
        "I am working on our school promotion video and hope to receive your likes~" to "https://www.instagrann.com/xxxuniversity_video/id=12333",
        "Hello, this is the subsidy given to you by the XXX company. Please click on the link to view it:" to "https://www.xxcompany.com/compensation",
        "Hello, this is the tuition subsidy given to you by the XXX University. Please check it:" to "https://www.xxuniversity.com/subsidy",
        "Hello, I heard that the university has recently issued subsidies to eligible students. You can also take a look:" to "https://www.xxxuniversitv.com/allowance?id=1",
        "Hello, the IT department of xxxx university has detected abnormal login behavior on your account. Please change your password as soon as possible on the following website:" to "https://www.tsdcuniversity.com/forget_password?index=1",
        "Hello, the IT department of xxxx company has detected abnormal login behavior on your account. Please change your password as soon as possible on the following website:" to "https://www.tsddafgrg.com/forget_password?in=check",
        "Hello, I heard that the government is subsidizing electricity bills. You can click on the following link to learn more:" to "https://www.glasgovv.gov.uk/bill",
        "Hello, our company can help young people start businesses. Are you interested?" to "Https://www.helpyoutostart.com/1234",
        "Hello, I am a whatsapp recruiter who can recommend jobs to you internally. You can click on the following link to view job opportunities." to "https://www.whatsapb.com/join/?dept=software-engineering",
        "Are you interested in becoming our food evaluator? Simply fill in your personal information and you will have the opportunity to participate in our food evaluation." to "Https://www.goodrestauranttest.com/join?name=food_evaluator",
        "Hello, there is an abnormality with your Whatsapp account. Please click on the following link to view the details:" to "https://faq.vvhatsapp.com/1313491802751163/?helpref=hc_fnav"
    )

    fun insertPhishingMessage() {
        ioScope.launch {
            for (i in phishingMessageList) {
                phishingMessageRepository.insertPhishingMessage(
                    PhishingMessage(
                        message = i.first + " " + i.second,
                        usedFlag = 0
                    )
                )
            }
        }
    }
    fun insertQuestion() {
        ioScope.launch {
            questionRepository.insertQuestion(
                Question(question = "If this message was sent by your friend, would you open the link?")
            )
            questionRepository.insertQuestion(
                Question(question = "If there is a preview of a link, can it help you identify whether there is a phishing message?")
            )
            questionRepository.insertQuestion(
                Question(question = "Have you carefully checked the URL of the link?")
            )
            questionRepository.insertQuestion(
                Question(question = "Do you think this is fishing messages?")
            )
            questionRepository.insertQuestion(
                Question(question = "If the messages comes from a well-know person in your school or company, would you believe it?")
            )
            questionRepository.insertQuestion(
                Question(question = "Will such message affect your mood?")
            )
            questionRepository.insertQuestion(
                Question(question = "If this message is a group chat message, would you believe it?")
            )
        }
    }
}