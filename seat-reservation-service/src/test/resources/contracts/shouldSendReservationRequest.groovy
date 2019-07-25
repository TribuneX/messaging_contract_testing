package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
Test
""")
    // Label by means of which the output message can be triggered
    label 'accepted_verification'
    // input to the contract
    input {
        // the contract will be triggered by a method
        triggeredBy('testSend()')
    }
    // output message of the contract
    outputMessage {
        // destination to which the output message will be sent
        sentTo 'seatReservation'
        // the body of the output message
        body(
                trainId: '12'
        )
        /*headers {
            messagingContentType(applicationJson())
        }*/
    }
}
