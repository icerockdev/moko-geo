/*
* Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
*/

import UIKit
import MultiPlatformLibrary
import CoreLocation

class TestViewController: UIViewController {
    @IBOutlet var textLabel: UILabel!
    
    private var viewModel: TrackerViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = TrackerViewModel(
            locationTracker: LocationTracker(
                permissionsController: PermissionsController(),
                accuracy: kCLLocationAccuracyBest
            )
        )
        
        viewModel.text.addObserver { [weak self] text in
            self?.textLabel.text = text as String?
        }
    }
    
    @IBAction func onStartPressed() {
        viewModel.onStartPressed()
    }
    
    @IBAction func onStopPressed() {
        viewModel.onStopPressed()
    }
}
