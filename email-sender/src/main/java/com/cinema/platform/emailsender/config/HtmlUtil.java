package com.cinema.platform.emailsender.config;

public final class HtmlUtil {

    private HtmlUtil() {
        throw new AssertionError();
    }

    static final String HTML_START_INFO =
            """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Ticket Confirmation</title>
                        <style>
                            body {
                                margin: 0;
                                padding: 0;
                                font-family: Arial, sans-serif;
                                background-color: #f4f4f4;
                            }
                            .email-container {
                                max-width: 600px;
                                margin: 0 auto;
                                background-color: #ffffff;
                            }
                            .header {
                                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                padding: 30px;
                                text-align: center;
                                color: #ffffff;
                            }
                            .header h1 {
                                margin: 0;
                                font-size: 28px;
                            }
                            .content {
                                padding: 30px;
                            }
                            .greeting {
                                font-size: 18px;
                                color: #333;
                                margin-bottom: 20px;
                            }
                            .confirmation-box {
                                background-color: #f8f9fa;
                                border-left: 4px solid #667eea;
                                padding: 20px;
                                margin: 20px 0;
                            }
                            .ticket-info {
                                margin: 20px 0;
                            }
                            .ticket-info p {
                                margin: 8px 0;
                                color: #555;
                            }
                            .ticket-info strong {
                                color: #333;
                            }
                            .movie-item {
                                background-color: #fff;
                                border: 2px solid #e0e0e0;
                                border-radius: 8px;
                                padding: 20px;
                                margin: 15px 0;
                            }
                            .movie-title {
                                font-size: 20px;
                                font-weight: bold;
                                color: #667eea;
                                margin-bottom: 10px;
                            }
                            .movie-details {
                                display: flex;
                                justify-content: space-between;
                                margin: 8px 0;
                                color: #555;
                            }
                            .seat-info {
                                background-color: #667eea;
                                color: white;
                                padding: 8px 16px;
                                border-radius: 20px;
                                display: inline-block;
                                margin-top: 10px;
                                font-weight: bold;
                            }
                            .total-section {
                                background-color: #667eea;
                                color: white;
                                padding: 20px;
                                text-align: center;
                                font-size: 24px;
                                font-weight: bold;
                                margin: 20px 0;
                                border-radius: 8px;
                            }
                            .footer {
                                background-color: #333;
                                color: #fff;
                                padding: 20px;
                                text-align: center;
                                font-size: 14px;
                            }
                            .qr-placeholder {
                                text-align: center;
                                margin: 20px 0;
                            }
                            .qr-box {
                                display: inline-block;
                                width: 150px;
                                height: 150px;
                                background-color: #f0f0f0;
                                border: 2px dashed #ccc;
                                line-height: 150px;
                                color: #999;
                            }
                        </style>
                    </head>
                    <body>
                    <div class="email-container">
                        <div class="header">
                            <h1>🎬 Ticket Confirmation</h1>
                            <p>Your cinema experience awaits!</p>
                        </div>
                    
                        <div class="content">
                            <div class="greeting">
                                Hello <span id="memberName">${memberName}</span>,
                            </div>
                    
                            <p>Thank you for your purchase! Your tickets have been confirmed.</p>
                    
                            <div class="confirmation-box">
                                <p><strong>Ticket ID:</strong> <span id="ticketId">${id}</span></p>
                                <p><strong>Purchase Date:</strong> <span id="payinTime">${payinTime}</span></p>
                            </div>
                    
                            <h2 style="color: #333; border-bottom: 2px solid #667eea; padding-bottom: 10px;">Projections</h2>
                            <div id="ticketItems">""";
    static final String PROJECTIONS_DIV = """
            <div class="movie-item">
                            <div class="movie-title">${movieName}</div>
                            <div class="movie-details">
                                <span><strong>Hall:</strong> ${hallName}</span>
                            </div>
                            <div class="movie-details">
                                <span><strong>Showtime:</strong> ${projectionTime}</span>
                            </div>
                            <div class="seat-info">Seat: ${seat}</div>
                        </div>
            """;
    static final String HTML_ENDING_AND_CLOSING_TAG =
            """
                    </div>
                    <div class="total-section">
                                Total: <span id="total">${total}</span>.00 RSD
                            </div>
                    
                            <div style="background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; margin: 20px 0; color: #856404;">
                                <strong>Important:</strong> Please arrive at least 15 minutes before showtime. Tickets are non-refundable.
                            </div>
                        </div>
                    
                        <div class="footer">
                            <p>Questions? Contact us at support@cinema.com</p>
                            <p>&copy; 2025 Cinema Platform. All rights reserved.</p>
                        </div>
                    </div>
                    </body>
                    </html>""";

}
