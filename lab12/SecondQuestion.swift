//
//  SecondQuestion.swift
//  lab12
//
//  Created by Student41 on 17/01/2022.
//

import UIKit

class SecondQuestion: UIViewController {

    @IBOutlet weak var tempLabel: UILabel!
    var labelText: String?
    override func viewDidLoad() {
        super.viewDidLoad()
        tempLabel.text = labelText
    }
}
