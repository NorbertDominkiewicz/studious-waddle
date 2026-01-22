import { useState } from "react";
import SurfacePlot from "./SurfacePlot";


export default function App() {
    const [method, setMethod] = useState("hooke"); // "hooke" or "gauss"

    const [hookeForm, setHookeForm] = useState({
        equation: "2.5*(x*x - y)*(x*x - y) + (1 - x)*(1 - x)",
        type: "MIN",
        x: 0,
        y: 0,
        step: 0.5,
        epsilon: 0.01
    });

    const [gaussForm, setGaussForm] = useState({
        equation: "2.5*(x*x - y)*(x*x - y) + (1 - x)*(1 - x)",
        x: 0,
        y: 0,
        epsilon: 0.01
    });

    const [result, setResult] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleHookeChange = (e) => {
        setHookeForm({
            ...hookeForm,
            [e.target.name]: e.target.value
        });
    };

    const handleGaussChange = (e) => {
        setGaussForm({
            ...gaussForm,
            [e.target.name]: e.target.value
        });
    };

    const submitHooke = async () => {
        setLoading(true);
        setResult(null);

        const payload = {
            equation: hookeForm.equation,
            type: hookeForm.type,
            x: Number(hookeForm.x),
            y: Number(hookeForm.y),
            step: Number(hookeForm.step),
            epsilon: Number(hookeForm.epsilon)
        };

        try {
            const res = await fetch("http://localhost:8080/api/hooke", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            const data = await res.json();
            setResult(data);
        } catch (e) {
            alert("Błąd połączenia z backendem");
        } finally {
            setLoading(false);
        }
    };

    const submitGauss = async () => {
        setLoading(true);
        setResult(null);

        const payload = {
            equation: gaussForm.equation,
            x: Number(gaussForm.x),
            y: Number(gaussForm.y),
            epsilon: Number(gaussForm.epsilon)
        };

        try {
            const res = await fetch("http://localhost:8080/api/gauss-seidel", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            const data = await res.json();
            setResult(data);
        } catch (e) {
            alert("Błąd połączenia z backendem");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen w-screen bg-gradient-to-br from-black via-slate-950 to-slate-900 p-12">

            <h1 className="text-5xl font-bold mb-8 text-center text-transparent bg-clip-text bg-gradient-to-r from-amber-400 via-yellow-500 to-amber-400">
                Optimization Methods
            </h1>

            {/* Method Selector */}
            <div className="flex justify-center gap-4 mb-8">
                <button
                    onClick={() => setMethod("hooke")}
                    className={`px-8 py-3 rounded-xl font-semibold transition-all ${
                        method === "hooke"
                            ? "bg-gradient-to-r from-amber-500 to-yellow-600 text-black shadow-lg shadow-amber-500/50"
                            : "bg-slate-800/60 text-white border border-slate-700 hover:border-amber-500/50"
                    }`}
                >
                    Hooke-Jeeves
                </button>
                <button
                    onClick={() => setMethod("gauss")}
                    className={`px-8 py-3 rounded-xl font-semibold transition-all ${
                        method === "gauss"
                            ? "bg-gradient-to-r from-amber-500 to-yellow-600 text-black shadow-lg shadow-amber-500/50"
                            : "bg-slate-800/60 text-white border border-slate-700 hover:border-amber-500/50"
                    }`}
                >
                    Gauss-Seidel
                </button>
            </div>

            <div className="flex gap-8 h-[calc(100vh-280px)]">

                {/* FORM - Left Side */}
                <div className="w-[450px] bg-slate-900/60 backdrop-blur-xl border border-amber-500/20 rounded-2xl p-8 shadow-2xl flex flex-col">

                    <div className="flex-1 overflow-y-auto">

                        {method === "hooke" ? (
                            // HOOKE-JEEVES FORM
                            <>
                                <label className="block mb-2 font-semibold text-amber-400">Funkcja</label>
                                <input
                                    name="equation"
                                    value={hookeForm.equation}
                                    onChange={handleHookeChange}
                                    className="w-full mb-6 p-3 bg-black/40 border border-slate-700 rounded-lg text-white placeholder-slate-500 focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                />

                                <label className="block mb-2 font-semibold text-amber-400">Typ optymalizacji</label>
                                <select
                                    name="type"
                                    value={hookeForm.type}
                                    onChange={handleHookeChange}
                                    className="w-full mb-6 p-3 bg-black/40 border border-slate-700 rounded-lg text-white focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                >
                                    <option value="MIN">Minimum</option>
                                    <option value="MAX">Maksimum</option>
                                </select>

                                <div className="grid grid-cols-2 gap-4 mb-6">
                                    <div>
                                        <label className="block mb-2 text-amber-400 font-medium">x start</label>
                                        <input
                                            name="x"
                                            type="number"
                                            value={hookeForm.x}
                                            onChange={handleHookeChange}
                                            className="w-full p-3 bg-black/40 border border-slate-700 rounded-lg text-white focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                        />
                                    </div>

                                    <div>
                                        <label className="block mb-2 text-amber-400 font-medium">y start</label>
                                        <input
                                            name="y"
                                            type="number"
                                            value={hookeForm.y}
                                            onChange={handleHookeChange}
                                            className="w-full p-3 bg-black/40 border border-slate-700 rounded-lg text-white focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                        />
                                    </div>
                                </div>

                                <div className="grid grid-cols-2 gap-4 mb-8">
                                    <div>
                                        <label className="block mb-2 text-amber-400 font-medium">Krok</label>
                                        <input
                                            name="step"
                                            type="number"
                                            value={hookeForm.step}
                                            onChange={handleHookeChange}
                                            className="w-full p-3 bg-black/40 border border-slate-700 rounded-lg text-white focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                        />
                                    </div>

                                    <div>
                                        <label className="block mb-2 text-amber-400 font-medium">Epsilon</label>
                                        <input
                                            name="epsilon"
                                            type="number"
                                            value={hookeForm.epsilon}
                                            onChange={handleHookeChange}
                                            className="w-full p-3 bg-black/40 border border-slate-700 rounded-lg text-white focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                        />
                                    </div>
                                </div>
                            </>
                        ) : (
                            // GAUSS-SEIDEL FORM
                            <>
                                <label className="block mb-2 font-semibold text-amber-400">Funkcja</label>
                                <input
                                    name="equation"
                                    value={gaussForm.equation}
                                    onChange={handleGaussChange}
                                    className="w-full mb-6 p-3 bg-black/40 border border-slate-700 rounded-lg text-white placeholder-slate-500 focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                />

                                <div className="grid grid-cols-2 gap-4 mb-6">
                                    <div>
                                        <label className="block mb-2 text-amber-400 font-medium">x start</label>
                                        <input
                                            name="x"
                                            type="number"
                                            value={gaussForm.x}
                                            onChange={handleGaussChange}
                                            className="w-full p-3 bg-black/40 border border-slate-700 rounded-lg text-white focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                        />
                                    </div>

                                    <div>
                                        <label className="block mb-2 text-amber-400 font-medium">y start</label>
                                        <input
                                            name="y"
                                            type="number"
                                            value={gaussForm.y}
                                            onChange={handleGaussChange}
                                            className="w-full p-3 bg-black/40 border border-slate-700 rounded-lg text-white focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                        />
                                    </div>
                                </div>

                                <div className="mb-8">
                                    <label className="block mb-2 text-amber-400 font-medium">Epsilon</label>
                                    <input
                                        name="epsilon"
                                        type="number"
                                        value={gaussForm.epsilon}
                                        onChange={handleGaussChange}
                                        className="w-full p-3 bg-black/40 border border-slate-700 rounded-lg text-white focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500/50"
                                    />
                                </div>
                            </>
                        )}

                        {result && (
                            <div className="mb-6 p-5 bg-black/60 rounded-xl border border-amber-500/30">
                                <div className="grid grid-cols-3 gap-4 text-center">
                                    <div>
                                        <p className="text-amber-400 text-sm mb-1 font-medium">x</p>
                                        <p className="text-white font-mono text-xl">{result.x?.toFixed(4)}</p>
                                    </div>
                                    <div>
                                        <p className="text-amber-400 text-sm mb-1 font-medium">y</p>
                                        <p className="text-white font-mono text-xl">{result.y?.toFixed(4)}</p>
                                    </div>
                                    <div>
                                        <p className="text-amber-400 text-sm mb-1 font-medium">Iteracje</p>
                                        <p className="text-white font-mono text-xl">{result.iterations}</p>
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>

                    <button
                        onClick={method === "hooke" ? submitHooke : submitGauss}
                        disabled={loading}
                        className="w-full bg-gradient-to-r from-amber-500 to-yellow-600 text-black font-bold p-4 rounded-xl hover:from-amber-400 hover:to-yellow-500 transition-all shadow-lg shadow-amber-500/30 hover:shadow-amber-500/50 disabled:opacity-50 disabled:cursor-not-allowed text-lg"
                    >
                        {loading ? "Liczenie..." : "Uruchom algorytm"}
                    </button>
                </div>

                {/* PLOT - Right Side - Takes remaining space */}
                <div className="flex-1 bg-slate-900/60 backdrop-blur-xl border border-amber-500/20 rounded-2xl p-8 shadow-2xl">
                    {result?.path ? (
                        <SurfacePlot path={result.path} />
                    ) : (
                        <div className="w-full h-full flex items-center justify-center">
                            <div className="text-center">
                                <div className="text-8xl mb-4">📈</div>
                                <p className="text-white text-xl">Uruchom algorytm aby zobaczyć wykres</p>
                            </div>
                        </div>
                    )}
                </div>

            </div>
        </div>
    );
}