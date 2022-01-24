//
//  ViewController.swift
//  lab12
//
//  Created by Student41 on 17/01/2022.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var temp: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier == "ToSecondQuestion1"){
        let dest = segue.destination as! SecondQuestion
            dest.labelText = temp.text
        } else {
          //  let dest = segue.destination as! SecondQuestion2
        }
    }
    
    @IBAction func returned (segue: UIStoryboardSegue) {
        print("Powrót do ekranu początkowego")
    }

    @IBAction func nextQuestion(_ sender: Any) {
        let temp_val = Double(temp.text!) ?? 36.6
        if (temp_val > 38.0){
            self.performSegue(withIdentifier: "ToSecondQuestion1", sender: self)
        } else {
            self.performSegue(withIdentifier: "ToSecondQuestion2", sender: self)
        
        }
    }
    
}

