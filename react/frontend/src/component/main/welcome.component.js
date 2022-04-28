export default function Welcome() {
    return(
        <div className="container">
            <div className="back" id="welcome-jumb">
                <div className="h-100 p-5 text-black bg-light rounded-3">
                    <h1>About Online Bank</h1>
                    <p>Online Bank is a global provider of financial services, comprised of over 20 credit
                        institutions and financial companies operating across all key areas of the financial markets.</p>
                </div>
            </div>
            <footer className="footer mt-auto py-3 bg-light">
                <div className="container">
                    <span className="text-muted">Zelenya-Novaya 10, 11
                        <br></br> Bank General Licence №199999
                        <br></br> Ⓒ Online Bank, 2022
                        <br></br> All rights reserved
                    </span>
                </div>
            </footer>
        </div>
    );
}