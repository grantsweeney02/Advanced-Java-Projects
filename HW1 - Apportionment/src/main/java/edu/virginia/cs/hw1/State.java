package edu.virginia.cs.hw1;

public class State implements Comparable<State> {
    private String name;
    private int population;
    private int representatives;
    private double remainder;

    public double getPriority() {
        return (population*1.0)/(Math.sqrt((representatives*(representatives+1))));
    }
    public State(String name, int population) {
        this.name = name;
        this.population = population;
    }

    @Override
    public String toString() {
        return name + " - " + String.valueOf(representatives);
    }

    public int getPopulation() {
        return population;
    }

    public String getName() {
        return name;
    }

    public int getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(int representatives) {
        this.representatives = representatives;
    }

    public double getRemainder() {
        return remainder;
    }

    public void setRemainder(double remainder) {
        this.remainder = remainder;
    }
    @Override
    public int compareTo(State o) {
        if (this.remainder > o.remainder)
            return -1;
        else if (this.remainder < o.remainder) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
