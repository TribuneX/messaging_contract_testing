import org.springframework.cloud.contract.spec.Contract
Contract.make {
    name "reservation request"
    label 'some_label'
    input {
        messageFrom('jms:seatReservation')
        messageBody([
                trainId: '12'
        ])
//        messageHeaders {
//            header('sample', 'header')
//        }
    }
    outputMessage {
        sentTo('jms:seatAvailability')
        body([
                trainId: '12',
                availableSeats: 3
        ])
//        headers {
//            header('BOOK-NAME', 'foo')
//        }
    }
}
