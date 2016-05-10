(ns fun-mode-sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]))
(def colors
  {:r 255 :g 255 :b 255})
(def min-r 10)
(def p {:x 500 :y 600})

(defn create-ship [a b c]
  {:x (q/random (q/width)) :y (q/random 0 (q/height)) :yv (q/random (- 1) (+ 1)) :xv (q/random (- 1) (+ 1)) :yacc 0 :xacc 0 :name "meme" :mass (q/random  200 300) :r a :g b :b c})

(defn setup []
  (q/frame-rate 1000)
  (q/background (q/random (:r colors)) (q/random (:g colors)) (q/random (:b colors)))
  { :u 1 :ps (conj (take 100 (repeatedly #(create-ship (q/random (:r colors)) (q/random (:g colors)) (q/random (:b colors))))))})

(defn update-pos [ship state]
  "updates the position based on stuff"
  (-> ship
      (assoc :x (+ (:x ship) (:xv ship)))
      (assoc :y (+ (:y ship) (:yv ship)))
      (assoc :yv (+ (:yv ship) (:yacc ship)))
      (assoc :xv (+ (:xv ship) (:xacc ship)))
      ;; (assoc :xacc (/  (- (:x p) (:x ship)) (* 0.01 (distance ship p))))
      ;; (assoc :yacc (/  (- (:y p) (:y ship)) (* 0.01 (distance ship p))))
      ))

(defn update [state]
  (-> state
      (update-in [:ps] (fn [a] (map #(update-pos % state) a)))))




(defn draw [state]

  ;;(q/background 255)
  (q/no-fill)
  (doseq [ship (:ps state)]
    ;;pcma (q/ellipse (:x ship) (:y ship) (:mass ship) (:mass ship))
    (doseq [pep (:ps state)]
      ;; fix parens in this part,
      (if (not= ship pep)
        (if (< (q/dist (:x ship) (:y ship) (:x pep) (:y pep)) (/  (+ (:mass pep) (:mass ship)) 2))
          (do
            (q/stroke (/ (+ (:r pep) (:r ship)) 2) (/ (+ (:g pep) (:g ship)) 2) (/ (+ (:b pep) (:b ship)) 2))
            ;;(q/stroke (mod (q/frame-count) 255))
            (q/line (:x ship) (:y ship) (:x pep) (:y pep))

            ;;(q/text-num (q/dist (:x ship) (:y ship) (:x pep) (:y pep)) (:x pep) (:y pep))
            ))))))

(q/defsketch example

  :title "memememem"
  :setup setup
  :draw draw
  :middleware [m/fun-mode]
  ;; :middleware [m/fun-mode]
  :size [1366 720]
  :update update)
