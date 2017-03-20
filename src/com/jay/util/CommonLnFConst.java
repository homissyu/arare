package com.jay.util;

import java.util.Vector;

public class CommonLnFConst{
    
    
    public CommonLnFConst(){
    }
    public static final String NiMBUS_LNF = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
    public static final String WINDOWS_LNF = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    public static final String MOTIF_LNF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    public static final String METAL_LNF = "javax.swing.plaf.metal.MetalLookAndFeel";
    public static final String WNF_LNF = "net.java.plaf.windows.WindowsLookAndFeel";
    public static final String SKIN_LNF = "com.l2fprod.gui.plaf.skin.SkinLookAndFeel";
    public static final String SKELETON_PLASTIC_LNF = "com.jgoodies.looks.plastic.PlasticLookAndFeel";
    public static final String SKELETON_WIN_LNF = "com.jgoodies.looks.windows.WindowsLookAndFeel";
    public static final String NAPKIN_LNF = "net.sourceforge.napkinlaf.NapkinLookAndFeel";
    public static final String OYOTA_LNF = "com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel";
    public static final String MACOS_LNF = "it.unitn.ing.swing.plaf.macos.MacOSLookAndFeel";
    public static final String IFN_LNF = "net.infonode.gui.laf.InfoNodeLookAndFeel";
    public static final String QUAQUA_LNF = "ch.randelshofer.quaqua.QuaquaLookAndFeel";
    public static final String KUNSTSOF_LNF = "com.incors.plaf.kunststoff.KunststoffLookAndFeel";
    public static final String FH_LNF = "com.shfarr.ui.plaf.fh.FhLookAndFeel";
    public static final String HIPPO_LNF = "se.diod.hippo.plaf.HippoLookAndFeel";
    public static final String PLASTIC_XP_LNF = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
    public static final String PLASTIC_3D_LNF = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel";
    public static final String PLASTIC_WINDOWS_LNF = "com.jgoodies.looks.windows.WindowsLookAndFeel";
    public static final String LIQUID_LNF = "com.birosoft.liquid.LiquidLookAndFeel";
    public static final String NIMROD_LNF = "com.nilo.plaf.nimrod.NimRODLookAndFeel";
    public static final String OFFICE_LNF = "org.fife.plaf.Office2003.Office2003LookAndFeel";
    public static final String PGS_LNF = "com.pagosoft.plaf.PgsLookAndFeel";
    public static final String TINY_LNF = "de.muntjak.tinylookandfeel.TinyLookAndFeel";
    public static final String SQUARENESS_LNF = "net.beeger.squareness.SquarenessLookAndFeel";
    public static final String SYNTHETICA_LNF = "de.javasoft.plaf.synthetica.SyntheticaLookAndFeel";
    public static final String SUBSTANCE_LNF = "org.jvnet.substance.SubstanceLookAndFeel";
    public static final String TONIC_LNF = "com.digitprop.tonic.TonicLookAndFeel";
    public static final String SMOOTH_METAL_LNF = "smooth.metal.SmoothLookAndFeel";
    public static final String SMOOTH_WINDOWS_LNF = "smooth.windows.SmoothLookAndFeel";
    public static final String SMART_LNF = "com.jtattoo.plaf.smart.SmartLookAndFeel";
    public static final String MCWIN_LNF = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";
    public static final String ACRYL_LNF = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
    public static final String AERO_LNF = "com.jtattoo.plaf.aero.AeroLookAndFeel";
    public static final String ALUMINIUM_LNF = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
    public static final String BERNSTEIN_LNF = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";
    public static final String FAST_LNF = "com.jtattoo.plaf.fast.FastLookAndFeel";
    public static final String GRAPHITE_LNF = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";
    public static final String HIFI_LNF = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
    public static final String LUNA_LNF = "com.jtattoo.plaf.luna.LunaLookAndFeel";
    public static final String MINT_LNF = "com.jtattoo.plaf.mint.MintLookAndFeel";
    public static final String NOIRE_LNF = "com.jtattoo.plaf.noire.NoireLookAndFeel";
    
    public Vector getLnFVector(){
        Vector ret = new Vector();
        this.getClass().getEnumConstants();
        return ret;
    }
}