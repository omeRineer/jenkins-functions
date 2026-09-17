def call(Map config = [:]) {
    def credId = config.credentialsId ?: 'MY_API_TOKEN'
    def endpoint = config.apiUrl ?: 'https://api.domain.com/v1/notifications'

    withCredentials([string(credentialsId: 'INTEGRATION_API', variable: 'API_URL'),
                     string(credentialsId: 'INTEGRATION_API_KEY', variable: 'API_KEY')]) {
        sh """
            curl --location '${API_URL}/Notifications/create' \
                --header 'Content-Type: application/json' \
                --header 'Fluxify-Api-Key: ${API_KEY}' \
                --data '{
                    "Title": "Deploy Tamamlandı",
                    "Summary": "${config.jobName} :: ${config.status}",
                    "Type": "info",
                    "To": {
                        "Users": null,
                        "Topics": [
                            "notification:system"
                        ]
                    }
                }'
        """
    }
}