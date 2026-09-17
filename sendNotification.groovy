def call(Map config = [:]) {
    // Değerler boş gelirse varsayılan olarak Jenkins ortam değişkenlerini kullanır
    def jobName = config.jobName ?: env.JOB_NAME
    def status = config.status ?: currentBuild.currentResult

    withCredentials([
        string(credentialsId: 'INTEGRATION_API', variable: 'API_URL'),
        string(credentialsId: 'INTEGRATION_API_KEY', variable: 'API_KEY')
    ]) {
        sh """
            curl --location "\${API_URL}/Notifications/create" \
                --header "Content-Type: application/json" \
                --header "Fluxify-Api-Key: \${API_KEY}" \
                --data '{
                    "Title": "Deploy Tamamlandı",
                    "Summary": "${jobName} :: ${status}",
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