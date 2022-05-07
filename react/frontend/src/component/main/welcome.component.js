import React from "react";

export default function Welcome() {
    return(
        <div className="container text-center ">
            <div className="col">
                <div className="row item-center back">
                    <div className="h-100 p-5 text-black bg-light rounded-3 w-50">
                        <h1>About Online Bank</h1>
                        <p>Online Bank is a global provider of financial services, comprised of over 20 credit
                            institutions and financial companies operating across all key areas of the financial markets.</p>
                    </div>
                </div>
                <div className="row item-center back">
                    <footer className="footer mt-auto py-3 bg-light rounded-3 w-25">
                        <div className="container">
                    <span className="text-muted">Zelenya-Novaya 10, 11
                        <br></br> Bank General Licence №199999
                        <br></br> Ⓒ Online Bank, 2022
                        <br></br> All rights reserved
                    </span>
                        </div>
                    </footer>
                </div>
            </div>
        </div>
    );
}