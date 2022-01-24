//
//  ViewController.swift
//  lab11
//
//  Created by Student41 on 10/01/2022.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    @IBOutlet weak var slider: UISlider!
    @IBOutlet weak var slider2: UISlider!
    @IBOutlet weak var input: UITextField!
    @IBOutlet weak var percentField: UITextField!
    @IBOutlet weak var sliderValue: UILabel!
    @IBOutlet weak var slider2Value: UILabel!
    @IBOutlet weak var result: UILabel!

    @IBAction func calculate(_ sender: Any) {
        if Double(input.text!) != nil {
            result.text = String(sum())
        }
    }

    @IBAction func sliderChangedValue(_ sender: Any) {
        sliderValue.text = String(slider.value)
    }
    @IBAction func slider2ChangedValue(_ sender: Any) {
        slider2Value.text = String(slider2.value)
        
    }
    
    func sum() -> Double {
        var temp_sum = Double(input.text!)
        let foodBonus = Double((slider2.value - 0.5) * 10)
        let serviceBonus = Double((slider.value - 0.5) * 10)
        if ((Double(percentField.text!) ?? 5.0) > 0){        temp_sum = temp_sum! + temp_sum! * ((Double(percentField.text!) ?? 5.0)/100.0)
            
        }
        if (foodBonus + serviceBonus < 0) {
            return temp_sum!
            
        }
        else
        {
            return Double(temp_sum! + foodBonus + serviceBonus)
        }
    }
    
}

