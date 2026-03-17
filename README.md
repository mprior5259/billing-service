## Tech Stack:
	Java 21
	SpringBoot
	Maven

## Running the App:
	Clone the repository, or extract project from zip, and open it in your IDE of choice. 
	IntelliJ is a good choice as it should be able to detect it as a Maven project on its own.
	Run BillingServiceApplication.java to start the server.
	The app will start on http://localhost:8080. No additional configuration needed.
	An API key is included in application.properties.
	
## Endpoints:
	All endpoints require the following header:
		X-API-Key: aaa-billing-key
		
	GET /api/billing/schedule/{policyId} - Retrieve the premium schedule for a policy
	GET /api/billing/delinquencies - Return a list of delinquent policies
	POST /api/billing/payment - Record a payment attempt
	POST /api/billing/retry/{transactionId} - Trigger a retry on a failed payment
	
## Testing:
	I would say Postman is probably the easiest way to run through the test cases. 
	I provided a collection(BillingService.postman_collection.json) and 
	environment file(BillingServiceEnv.postman_environment.json) in the postman folder 
	for ease of use. Just import both files into Postman and select BillingServiceEnv environment 
	from the top right environment dropdown.
	
	Test 1:
		* Retrieve schedule for policy 1001 - http://localhost:8080/api/billing/schedule/1001
		* Check delinquent policies - http://localhost:8080/api/billing/delinquencies
			- Confirm policy 1001 exists in the list
		* Submit a payment for the policy - http://localhost:8080/api/billing/payment
			- {
				"policyId": 1001,
				"amount": 80.65,
				"notes": "Submitting payment"
			  }
			- The mock payment processor will return a failed result and the transactionId will be in the response
		* Retry payment - http://localhost:8080/api/billing/retry/{transactionId}
			- transactionId can be found from response in previous step
			- The mock payment processor will return a success result
			
	Test 2:
		* Submit a payment for the policy 1002 - http://localhost:8080/api/billing/payment
			- The mock payment processor will return a failed result. The record for this payment is stored in a list in memory
			- {
				"policyId": 1002,
				"amount": 235.05,
				"notes": "Submitting payment"
			  }
		* Retry payment for policy 1002 - http://localhost:8080/api/billing/retry/{transactionId}
			- transactionId can be found from response in previous step. Payment attempts and retries get temporarily stored in 
			  memory and will persist as long as the app continues running
			- The mock payment processor will return a success result
			
	Test 3: Restart App
		* Retrieve schedule for policy 9999 - http://localhost:8080/api/billing/schedule/9999
			- This will return failed with message: "Policy not found."
			- Record doesn't exist in our mock data list
	Test 4:
		* Make a payment with malformed json data - http://localhost:8080/api/billing/payment
			- {
				"policyId": 0,
				"amount": 35.00,
				"notes": "Submitting payment"
			  }
			 - Will return a failed status with "Invalid policy Id." message
			 
			 - {
				"policyId": 1001,
				"amount": 0,
				"notes": "Submitting payment"
			  }
			  - Will return a failed status with "Invalid amount." message
			  
	Test 5:
		* Retry a payment for a payment that was already processed successfully - http://localhost:8080/api/billing/retry/2
			- Use transactionId = 2 (saved in mock data with a success status)
			- Will return a failed status with message "Payment is not in a failed state."
			
	Test 6: Restart App
		* Submit a payment for policy 1002 - http://localhost:8080/api/billing/payment
			- {
				"policyId": 1002,
				"amount": 235.05,
				"notes": "Submitting payment"
			  }
			- Mock payment processor will return a failed result.
			- Copy the transactionId
		* Retry payment using the transactionId from STEP 1 - http://localhost:8080/api/billing/retry/{transactionId}
			- Will return a success result
		* Retry payment again using the same transactionId from STEP 1 - http://localhost:8080/api/billing/retry/{transactionId}
			- Will return a failed result as the transactionId was already used in a retry
			
	Test 7: 
		* Replace header api key in environment with random value
		* Hit any endpoint. Get delinquencies is the easiest - http://localhost:8080/api/billing/delinquencies
			- Will return a 401 error with an "Unauthorized." message
			

## What I Would Do With More Time:
    Database - Replace the in memory mock data with a real database. The service layer is structured so 
		repository calls would be easy to drop in.
    Service Queue - Introduce a service queue between the billing service and the payment processor to handle 
		rate limiting to avoid hammering the 3rd party payment service with requests.
    Max retry limit - Implement logic to track and handle a retry limit for payment processing. 
		Flag the policy for manual review if the limit is ever reached.
    Authentication - Replace the simple API key header check with proper bearer token based authentication.
