(ns logistic.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def width 512)

(defn logistic-map [pt]
  (let [x (:x pt)
        r (:r pt)]
    (assoc pt
           :x (* r (- 1 x) x))))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  {:points (for [r (range 2.5 4 (/ 1.5 width 1.0)) x (range 0 1 (/ 1.0 width))] {:r r :x x})})

(defn update-state [state]
  (let [points (:points state)]
    (assoc state
           :points (map logistic-map points))))

(defn draw-points [points]
  (if (not (empty? points))
    (let [pt (first points)
          x (* width (:x pt))
          y (* (/ (- (:r pt) 2.5) 1.5) width)
          ;dummy (println (str "tick\n"
          ;                    (into [] points)))
          ]
      (q/point x y)
      ;(println (str "Drawing (" x ", " y ")\n"))
      (recur (rest points)))))

(defn draw-state [state]
  (q/background 0)
  (q/stroke 255)
  (draw-points (:points state)))


(q/defsketch logistic
  :title "Bifurcation"
  :size [width width]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
