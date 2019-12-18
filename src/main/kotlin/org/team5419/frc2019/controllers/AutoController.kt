// package org.team5419.frc2019.controllers

// import org.team5419.fault.Controller
// import org.team5419.fault.auto.Routine
// import org.team5419.fault.auto.Action
// import org.team5419.fault.math.geometry.Rotation2d
// import org.team5419.frc2019.subsystems.Drivetrain

// public class AutoController (routine: Routine) : Controller {

//     private val mRoutine: Routine
//     private var currentAction: Action?
//     private var isFinished: Boolean

//     init {
//         mRoutine = routine
//         isFinished = false
//         currentAction = null
//     }

//     public override fun start() {
//         // TODO choose routine from dashboard
//         // println("auto controller starting")
//         // reset()
//         // // currentRoutine = mRoutines.rocketRight
//         // Drivetrain.isBraking = true

//         // Drivetrain.setTurn( Rotation2d(mRoutine.startPose.rotation) )
//         // Drivetrain.setPosition(mRoutine.startPose.translation)
//         // currentAction = mRoutine.getCurrentAction()
//         // currentAction!!.start()
//     }

//     public override fun update() {
//         // if (isFinished) {
//         //     return
//         // }
//         // if (mRoutine.isLastStep() && currentAction!!.next()) {
//         //     currentAction!!.finish()
//         //     isFinished = true
//         //     return
//         // }
//         // if (currentAction == null) {
//         //     currentAction = mRoutine.getCurrentAction()
//         //     currentAction!!.start()
//         // } else if (currentAction!!.next()) {
//         //     currentAction!!.finish()
//         //     mRoutine.advanceRoutine()
//         //     currentAction = currentRoutine.getCurrentAction()
//         //     currentAction!!.start()
//         // } else {
//         //     currentAction!!.update()
//         // }
//     }

//     public override fun reset() {
//         // currentAction = null
//         // isFinished = false
//     }
// }
