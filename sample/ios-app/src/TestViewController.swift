/*
* Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
*/

import UIKit
import MultiPlatformLibrary
import CoreLocation

class TestViewController: UIViewController {
    @IBOutlet var textLocationLabel: UILabel!
    @IBOutlet var textExtendedLocationLabel: UILabel!

    private var viewModel: TrackerViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = TrackerViewModel(
            locationTracker: LocationTracker(
                permissionsController: PermissionsController(),
                accuracy: kCLLocationAccuracyBest
            )
        )
        
        viewModel.textLocation.addObserver { [weak self] text in
            self?.textLocationLabel.text = text as String?
        }
        
        viewModel.textExtendedLocation.addObserver { [weak self] text in
            self?.textExtendedLocationLabel.text = text as String?
        }
    }
    
    @IBAction func onStartPressed() {
        viewModel.onStartPressed()
    }
    
    @IBAction func onStopPressed() {
        viewModel.onStopPressed()
    }
}
