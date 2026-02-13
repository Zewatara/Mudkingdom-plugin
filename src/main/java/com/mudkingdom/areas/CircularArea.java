package com.mudkingdom.areas;

import org.bukkit.Location;

public class CircularArea {
    
    private final String name;
    private final Location center;
    private final double radius;
    
    public CircularArea(String name, Location center, double radius) {
        this.name = name;
        this.center = center;
        this.radius = radius;
    }
    
    public boolean isInside(Location location) {
        if (!location.getWorld().equals(center.getWorld())) {
            return false;
        }
        
        double dx = location.getX() - center.getX();
        double dz = location.getZ() - center.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        
        return distance <= radius;
    }
    
    public String getName() {
        return name;
    }
}