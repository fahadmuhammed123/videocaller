import UIKit
import React
import Foundation

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        let rootView = RNViewManager.sharedInstance.viewForModule("Video", initialProperties: ["id": "trackId"])
        
        self.view.addSubview(rootView)
        rootView.frame = self.view.bounds
    }
}
